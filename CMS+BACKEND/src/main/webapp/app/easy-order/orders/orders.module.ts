import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EasyOrderSharedModule } from 'app/shared';

import { OrdersComponent } from './';
import { ordersRoute } from './';

const ORDERS_STATES = [...ordersRoute];

@NgModule({
    declarations: [OrdersComponent],
    imports: [EasyOrderSharedModule, RouterModule.forChild(ORDERS_STATES)],
    entryComponents: [OrdersComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrdersModule {
    constructor() {}
}
