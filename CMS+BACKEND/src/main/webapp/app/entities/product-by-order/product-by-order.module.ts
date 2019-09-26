import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    ProductByOrderComponent,
    ProductByOrderDetailComponent,
    ProductByOrderUpdateComponent,
    ProductByOrderDeletePopupComponent,
    ProductByOrderDeleteDialogComponent,
    productByOrderRoute,
    productByOrderPopupRoute
} from './';

const ENTITY_STATES = [...productByOrderRoute, ...productByOrderPopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductByOrderComponent,
        ProductByOrderDetailComponent,
        ProductByOrderUpdateComponent,
        ProductByOrderDeleteDialogComponent,
        ProductByOrderDeletePopupComponent
    ],
    entryComponents: [
        ProductByOrderComponent,
        ProductByOrderUpdateComponent,
        ProductByOrderDeleteDialogComponent,
        ProductByOrderDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderProductByOrderModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
