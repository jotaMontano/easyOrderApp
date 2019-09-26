import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
declare var $: any;
import '../../../content/js/app-sidebar.js';
import { AccountService, JhiLanguageHelper, LoginModalService, LoginService } from 'app/core';
import { JhiLanguageService } from 'ng-jhipster';
import { ProfileService } from 'app/layouts';
import { SessionStorageService } from 'ngx-webstorage';
@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styles: []
})
export class SidebarComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    sidebar: any;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router
    ) {}

    ngOnInit() {
        this.sidebar = window['sidebarHolder'];
        // this.principal.identity().then((account) => {
        //     this.account = account;
        // });
        this.sidebar();
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
        // return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        // this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }
}
