import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';

import { OrdersComponent } from './';
import { OrderOkResolve } from 'app/entities/order-ok';

export const ordersRoute: Routes = [
    {
        path: '',
        component: OrdersComponent,
        resolve: {
            orders: OrderOkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrder.orders.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
