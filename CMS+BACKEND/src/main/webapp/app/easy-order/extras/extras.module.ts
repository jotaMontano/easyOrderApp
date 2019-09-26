import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CurrencyMaskModule } from 'ng2-currency-mask';
import { CurrencyMaskConfig } from 'ng2-currency-mask/src/currency-mask.config';
import { CURRENCY_MASK_CONFIG } from 'ngx-currency/src/currency-mask.config';
import { EasyOrderSharedModule } from 'app/shared';
import { ExtrasComponent, ExtrasUpdatePopupComponent, ExtrasUpdateComponent, extrasRoute } from './';

const ENTITY_STATES = [...extrasRoute];

export const CustomCurrencyMaskConfig: CurrencyMaskConfig = {
    align: 'right',
    allowNegative: true,
    decimal: ',',
    precision: 2,
    prefix: 'R₡ ',
    suffix: '',
    thousands: '.'
};
@NgModule({
    imports: [EasyOrderSharedModule, CurrencyMaskModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ExtrasComponent, ExtrasUpdateComponent, ExtrasUpdatePopupComponent],
    entryComponents: [ExtrasComponent, ExtrasUpdateComponent, ExtrasUpdatePopupComponent],
    providers: [
        { provide: JhiLanguageService, useClass: JhiLanguageService },
        { provide: CURRENCY_MASK_CONFIG, useValue: CustomCurrencyMaskConfig }
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExtrasModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
