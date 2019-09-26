import '../../../content/js/landing-js/jquery-2.2.4.min.js';
import '../../../content/js/landing-js/scroll.js';
import '../../../content/js/landing-js/main.js';

import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { LoginModalService, AccountService, Account, LoginService, JhiLanguageHelper } from 'app/core';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-landing',
    templateUrl: './landing.component.html',
    styleUrls: ['landing.scss']
})
export class LandingComponent implements OnInit {
    main: any;
    scroll: any;
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private loginModalService: LoginModalService
    ) {}

    ngOnInit() {
        this.main = window['mainHolder'];
        this.main();
        this.scroll = window['scrollHolder'];
        this.scroll();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
