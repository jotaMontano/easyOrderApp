import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    TopComponent,
    TopDetailComponent,
    TopUpdateComponent,
    TopDeletePopupComponent,
    TopDeleteDialogComponent,
    topRoute,
    topPopupRoute
} from './';

const ENTITY_STATES = [...topRoute, ...topPopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TopComponent, TopDetailComponent, TopUpdateComponent, TopDeleteDialogComponent, TopDeletePopupComponent],
    entryComponents: [TopComponent, TopUpdateComponent, TopDeleteDialogComponent, TopDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderTopModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
