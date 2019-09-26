import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AngularFireModule } from '@angular/fire';
import { FIREBASE } from 'app/app.constants';
@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'orders',
                loadChildren: './orders/orders.module#OrdersModule'
            },
            {
                path: 'products',
                loadChildren: './products/products.module#EasyOrderProductsModule'
            },
            {
                path: 'extras',
                loadChildren: './extras/extras.module#ExtrasModule'
            },
            {
                path: 'categories',
                loadChildren: './categories/categories.module#CategoriesModule'
            },
            {
                path: 'discounts',
                loadChildren: './discounts/discounts.module#DiscountsModule'
            }
        ]),
        AngularFireModule.initializeApp(FIREBASE.config)
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderModule {}
