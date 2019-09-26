import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import {
    ListOfValueComponent,
    ListOfValueDetailComponent,
    ListOfValueUpdateComponent,
    ListOfValueDeletePopupComponent,
    ListOfValueDeleteDialogComponent,
    listOfValueRoute,
    listOfValuePopupRoute
} from './';

const ENTITY_STATES = [...listOfValueRoute, ...listOfValuePopupRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ListOfValueComponent,
        ListOfValueDetailComponent,
        ListOfValueUpdateComponent,
        ListOfValueDeleteDialogComponent,
        ListOfValueDeletePopupComponent
    ],
    entryComponents: [ListOfValueComponent, ListOfValueUpdateComponent, ListOfValueDeleteDialogComponent, ListOfValueDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderListOfValueModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
