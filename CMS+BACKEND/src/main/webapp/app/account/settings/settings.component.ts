import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService, JhiLanguageHelper } from 'app/core';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { ICategory } from 'app/shared/model/category.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { IExtra } from 'app/shared/model/extra.model';
import { IOrderHistory } from 'app/shared/model/order-history.model';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['../register/register.component.css']
})
export class SettingsComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];
    client: any;

    constructor(
        private accountService: AccountService,
        protected clientService: ClientService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {}

    ngOnInit() {
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

        this.accountService.identity().then(account => {
            this.settingsAccount = this.copyAccount(account);
            this.clientService.findByUserId(account.id).subscribe(response => {
                this.client = response.body;
            });
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
    }

    save() {
        this.accountService.save(this.settingsAccount).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                setTimeout(() => (this.success = ''), 3500);
                this.accountService.identity(true).then(account => {
                    this.settingsAccount = this.copyAccount(account);
                });
                this.client.email = this.settingsAccount.email;
                this.clientService.update(this.client).subscribe();
                this.languageService.getCurrent().then(current => {
                    if (this.settingsAccount.langKey !== current) {
                        this.languageService.changeLanguage(this.settingsAccount.langKey);
                    }
                });
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
                setTimeout(() => (this.error = ''), 3500);
            }
        );
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }
}
