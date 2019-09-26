import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    ExtraInLineComponent,
    ExtraInLineDetailComponent,
    ExtraInLineUpdateComponent,
    ExtraInLineDeletePopupComponent,
    ExtraInLineDeleteDialogComponent,
    extraInLineRoute,
    extraInLinePopupRoute
} from './';

const ENTITY_STATES = [...extraInLineRoute, ...extraInLinePopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExtraInLineComponent,
        ExtraInLineDetailComponent,
        ExtraInLineUpdateComponent,
        ExtraInLineDeleteDialogComponent,
        ExtraInLineDeletePopupComponent
    ],
    entryComponents: [ExtraInLineComponent, ExtraInLineUpdateComponent, ExtraInLineDeleteDialogComponent, ExtraInLineDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderExtraInLineModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
