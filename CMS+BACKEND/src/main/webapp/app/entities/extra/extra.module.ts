import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    ExtraComponent,
    ExtraDetailComponent,
    ExtraUpdateComponent,
    ExtraDeletePopupComponent,
    ExtraDeleteDialogComponent,
    extraRoute,
    extraPopupRoute
} from './';

const ENTITY_STATES = [...extraRoute, ...extraPopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ExtraComponent, ExtraDetailComponent, ExtraUpdateComponent, ExtraDeleteDialogComponent, ExtraDeletePopupComponent],
    entryComponents: [ExtraComponent, ExtraUpdateComponent, ExtraDeleteDialogComponent, ExtraDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderExtraModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
