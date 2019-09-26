import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Observable, Observer, Subscription } from 'rxjs';

import { CSRFService } from '../auth/csrf.service';
import { WindowRef } from './window.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

@Injectable({ providedIn: 'root' })
export class JhiTrackerService {
    stompClient = null;
    subscriber = null;
    subscriberDiscount = null;
    subscriberOrder = null;
    connection: Promise<any>;
    connectedPromise: any;
    listener: Observable<any>;
    listenerDiscount: Observable<any>;
    listenerOrder: Observable<any>;
    listenerObserver: Observer<any>;
    listenerObserverDiscount: Observer<any>;
    listenerObserverOrder: Observer<any>;
    alreadyConnectedOnce = false;
    private subscription: Subscription;

    constructor(
        private router: Router,
        private authServerProvider: AuthServerProvider,
        private $window: WindowRef,
        // tslint:disable-next-line: no-unused-variable
        private csrfService: CSRFService
    ) {
        this.connection = this.createConnection();
        this.listener = this.createListener();
        this.listenerDiscount = this.createListenerDiscount();
        this.listenerOrder = this.createListenerOrder();
    }

    connect() {
        if (this.connectedPromise === null) {
            this.connection = this.createConnection();
        }
        // building absolute path so that websocket doesn't fail when deploying with a context path
        const loc = this.$window.nativeWindow.location;
        let url;
        url = '//' + loc.host + loc.pathname + 'websocket/tracker';
        const authToken = this.authServerProvider.getToken();
        if (authToken) {
            url += '?access_token=' + authToken;
        }
        const socket = new SockJS(url);
        this.stompClient = Stomp.over(socket);
        const headers = {};
        this.stompClient.connect(headers, () => {
            this.connectedPromise('success');
            this.connectedPromise = null;
            this.sendActivity();
            if (!this.alreadyConnectedOnce) {
                this.subscription = this.router.events.subscribe(event => {
                    if (event instanceof NavigationEnd) {
                        this.sendActivity();
                    }
                });
                this.alreadyConnectedOnce = true;
            }
        });
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
            this.stompClient = null;
        }
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        this.alreadyConnectedOnce = false;
    }

    receive() {
        return this.listener;
    }

    receiveDiscount() {
        return this.listenerDiscount;
    }

    receiveOrder() {
        return this.listenerOrder;
    }

    sendActivity() {
        if (this.stompClient !== null && this.stompClient.connected) {
            this.stompClient.send(
                '/topic/activity', // destination
                JSON.stringify({ page: this.router.routerState.snapshot.url }), // body
                {} // header
            );
        }
    }

    subscribe() {
        this.connection.then(() => {
            this.subscriber = this.stompClient.subscribe('/topic/tracker', data => {
                this.listenerObserver.next(JSON.parse(data.body));
            });
        });
    }

    subscribeDiscount() {
        this.connection.then(() => {
            this.subscriberDiscount = this.stompClient.subscribe('/topic/discount', data => {
                this.listenerObserverDiscount.next(data.body);
            });
        });
    }

    subscribeOrder() {
        this.connection.then(() => {
            this.subscriberOrder = this.stompClient.subscribe('/topic/order', data => {
                this.listenerObserverOrder.next(data.body);
            });
        });
    }

    unsubscribe() {
        if (this.subscriber !== null) {
            this.subscriber.unsubscribe();
        }
        this.listener = this.createListener();
    }

    unsubscribeDiscount() {
        if (this.subscriberDiscount !== null) {
            this.subscriberDiscount.unsubscribe();
        }
        this.listenerDiscount = this.createListenerDiscount();
    }

    unsubscribeOrder() {
        if (this.subscriberOrder !== null) {
            this.subscriberOrder.unsubscribe();
        }
        this.listenerOrder = this.createListenerOrder();
    }

    private createListener(): Observable<any> {
        return new Observable(observer => {
            this.listenerObserver = observer;
        });
    }

    private createListenerDiscount(): Observable<any> {
        return new Observable(observer => {
            this.listenerObserverDiscount = observer;
        });
    }

    private createListenerOrder(): Observable<any> {
        return new Observable(observer => {
            this.listenerObserverOrder = observer;
        });
    }

    private createConnection(): Promise<any> {
        return new Promise((resolve, reject) => (this.connectedPromise = resolve));
    }
}
