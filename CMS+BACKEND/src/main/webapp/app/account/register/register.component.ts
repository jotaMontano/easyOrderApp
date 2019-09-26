import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { IUser, LoginModalService, UserService } from 'app/core';
import { Register } from './register.service';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';
import { ICategory } from 'app/shared/model/category.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { IExtra } from 'app/shared/model/extra.model';
import { IOrderHistory } from 'app/shared/model/order-history.model';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    registerClient: any;
    success: boolean;
    user: IUser;
    client: IClient;
    modalRef: NgbModalRef;

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        protected userService: UserService,
        protected clientService: ClientService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {}

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.registerClient = {};
        this.client = new class implements IClient {
            categories: ICategory[];
            discounts: IDiscount[];
            email: string;
            extras: IExtra[];
            id: number;
            name: string;
            orderHistories: IOrderHistory[];
            orders: IOrderOk[];
            products: IProduct[];
            userId: number;
        }();
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    register() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(
                    () => {
                        this.userService.find(this.registerAccount.login).subscribe(
                            response => {
                                this.user = response.body;
                                this.client.email = this.user.email;
                                this.client.name = this.registerClient.name;
                                this.client.userId = this.user.id;
                                this.clientService.create(this.client).subscribe(
                                    () => {
                                        this.success = true;
                                    },
                                    responseClient => this.processError(responseClient)
                                );
                            },
                            responseUser => this.processError(responseUser)
                        );
                    },
                    response => this.processError(response)
                );
            });
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response: HttpErrorResponse) {
        this.success = null;
        console.log(response.error.type);
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
            setTimeout(() => (this.errorUserExists = ''), 3500);
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
            setTimeout(() => (this.errorEmailExists = ''), 3500);
        } else {
            this.error = 'ERROR';
            setTimeout(() => (this.error = ''), 3500);
        }
    }
}
