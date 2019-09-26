import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    OrderOkComponent,
    OrderOkDetailComponent,
    OrderOkUpdateComponent,
    OrderOkDeletePopupComponent,
    OrderOkDeleteDialogComponent,
    orderOkRoute,
    orderOkPopupRoute
} from './';

const ENTITY_STATES = [...orderOkRoute, ...orderOkPopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrderOkComponent,
        OrderOkDetailComponent,
        OrderOkUpdateComponent,
        OrderOkDeleteDialogComponent,
        OrderOkDeletePopupComponent
    ],
    entryComponents: [OrderOkComponent, OrderOkUpdateComponent, OrderOkDeleteDialogComponent, OrderOkDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderOrderOkModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
