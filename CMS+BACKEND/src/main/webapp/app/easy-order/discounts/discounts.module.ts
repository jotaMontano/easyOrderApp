import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import { DiscountsComponent, DiscountsUpdatePopupComponent, DiscountsUpdateComponent, discountsRoute } from './';

const ENTITY_STATES = [...discountsRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DiscountsComponent, DiscountsUpdateComponent, DiscountsUpdatePopupComponent],
    entryComponents: [DiscountsComponent, DiscountsUpdateComponent, DiscountsUpdatePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscountsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
