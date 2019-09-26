import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AngularFireStorageModule, StorageBucket } from '@angular/fire/storage';
import { AngularFireModule } from '@angular/fire';
import { FIREBASE } from 'app/app.constants';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'product',
                loadChildren: './product/product.module#EasyOrderProductModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'extra',
                loadChildren: './extra/extra.module#EasyOrderExtraModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'order-ok',
                loadChildren: './order-ok/order-ok.module#EasyOrderOrderOkModule'
            },
            {
                path: 'product-by-order',
                loadChildren: './product-by-order/product-by-order.module#EasyOrderProductByOrderModule'
            },
            {
                path: 'order-history',
                loadChildren: './order-history/order-history.module#EasyOrderOrderHistoryModule'
            },
            {
                path: 'top',
                loadChildren: './top/top.module#EasyOrderTopModule'
            },
            {
                path: 'list-of-value',
                loadChildren: './list-of-value/list-of-value.module#EasyOrderListOfValueModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#EasyOrderProductModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'extra',
                loadChildren: './extra/extra.module#EasyOrderExtraModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'order-ok',
                loadChildren: './order-ok/order-ok.module#EasyOrderOrderOkModule'
            },
            {
                path: 'product-by-order',
                loadChildren: './product-by-order/product-by-order.module#EasyOrderProductByOrderModule'
            },
            {
                path: 'order-history',
                loadChildren: './order-history/order-history.module#EasyOrderOrderHistoryModule'
            },
            {
                path: 'top',
                loadChildren: './top/top.module#EasyOrderTopModule'
            },
            {
                path: 'list-of-value',
                loadChildren: './list-of-value/list-of-value.module#EasyOrderListOfValueModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#EasyOrderProductModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'extra',
                loadChildren: './extra/extra.module#EasyOrderExtraModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'order-ok',
                loadChildren: './order-ok/order-ok.module#EasyOrderOrderOkModule'
            },
            {
                path: 'product-by-order',
                loadChildren: './product-by-order/product-by-order.module#EasyOrderProductByOrderModule'
            },
            {
                path: 'order-history',
                loadChildren: './order-history/order-history.module#EasyOrderOrderHistoryModule'
            },
            {
                path: 'top',
                loadChildren: './top/top.module#EasyOrderTopModule'
            },
            {
                path: 'list-of-value',
                loadChildren: './list-of-value/list-of-value.module#EasyOrderListOfValueModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#EasyOrderProductModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'extra',
                loadChildren: './extra/extra.module#EasyOrderExtraModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'product-by-order',
                loadChildren: './product-by-order/product-by-order.module#EasyOrderProductByOrderModule'
            },
            {
                path: 'extra-in-line',
                loadChildren: './extra-in-line/extra-in-line.module#EasyOrderExtraInLineModule'
            },
            {
                path: 'list-of-value',
                loadChildren: './list-of-value/list-of-value.module#EasyOrderListOfValueModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#EasyOrderProductModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'extra',
                loadChildren: './extra/extra.module#EasyOrderExtraModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'order-ok',
                loadChildren: './order-ok/order-ok.module#EasyOrderOrderOkModule'
            },
            {
                path: 'product-by-order',
                loadChildren: './product-by-order/product-by-order.module#EasyOrderProductByOrderModule'
            },
            {
                path: 'extra-in-line',
                loadChildren: './extra-in-line/extra-in-line.module#EasyOrderExtraInLineModule'
            },
            {
                path: 'order-history',
                loadChildren: './order-history/order-history.module#EasyOrderOrderHistoryModule'
            },
            {
                path: 'top',
                loadChildren: './top/top.module#EasyOrderTopModule'
            },
            {
                path: 'list-of-value',
                loadChildren: './list-of-value/list-of-value.module#EasyOrderListOfValueModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EasyOrderCategoryModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            },
            {
                path: 'discount',
                loadChildren: './discount/discount.module#EasyOrderDiscountModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#EasyOrderClientModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ]),
        BrowserModule,
        AngularFireModule.initializeApp(FIREBASE.config),
        AngularFireStorageModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EasyOrderEntityModule {}
