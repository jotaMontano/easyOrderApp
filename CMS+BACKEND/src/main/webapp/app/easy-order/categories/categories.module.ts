import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EasyOrderSharedModule } from 'app/shared';
import { CategoriesComponent, CategoriesUpdatePopupComponent, CategoriesUpdateComponent, categoriesRoute } from './';

const ENTITY_STATES = [...categoriesRoute];

@NgModule({
    imports: [EasyOrderSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CategoriesComponent, CategoriesUpdateComponent, CategoriesUpdatePopupComponent],
    entryComponents: [CategoriesComponent, CategoriesUpdateComponent, CategoriesUpdatePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CategoriesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
