import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';

import { CurrencyMaskModule } from 'ng2-currency-mask';
import { CurrencyMaskConfig } from 'ng2-currency-mask/src/currency-mask.config';
import { CURRENCY_MASK_CONFIG } from 'ngx-currency/src/currency-mask.config';
import { productsPopupRoute, productsRoute } from 'app/easy-order/products/products.route';
import { ProductsComponent } from 'app/easy-order/products/products.component';
import { ProductsDetailComponent } from 'app/easy-order/products/products-detail.component';
import { ProductsUpdateComponent } from 'app/easy-order/products/products-update.component';
import { ProductsDeleteDialogComponent, ProductsDeletePopupComponent } from 'app/easy-order/products/products-delete-dialog.component';

import { FIREBASE } from 'app/app.constants';

export const CustomCurrencyMaskConfig: CurrencyMaskConfig = {
    align: 'right',
    allowNegative: true,
    decimal: ',',
    precision: 2,
    prefix: 'R₡ ',
    suffix: '',
    thousands: '.'
};
const ENTITY_STATES = [...productsRoute, ...productsPopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, CurrencyMaskModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductsComponent,
        ProductsDetailComponent,
        ProductsUpdateComponent,
        ProductsDeleteDialogComponent,
        ProductsDeletePopupComponent
    ],
    entryComponents: [ProductsComponent, ProductsUpdateComponent, ProductsDeleteDialogComponent, ProductsDeletePopupComponent],
    providers: [
        { provide: JhiLanguageService, useClass: JhiLanguageService },
        { provide: CURRENCY_MASK_CONFIG, useValue: CustomCurrencyMaskConfig }
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderProductsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
