import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Extra } from 'app/shared/model/extra.model';
import { ExtrasService } from './extras.service';
import { ExtrasComponent } from './extras.component';
import { ExtrasUpdatePopupComponent } from './extras-update.component';
import { IExtra } from 'app/shared/model/extra.model';

@Injectable({ providedIn: 'root' })
export class ExtrasResolve implements Resolve<IExtra> {
    constructor(private service: ExtrasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExtra> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Extra>) => response.ok),
                map((extra: HttpResponse<Extra>) => extra.body)
            );
        }
        return of(new Extra());
    }
}

export const extrasRoute: Routes = [
    {
        path: '',
        component: ExtrasComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExtrasUpdatePopupComponent,
        resolve: {
            extra: ExtrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: ':id/edit',
        component: ExtrasUpdatePopupComponent,
        resolve: {
            extra: ExtrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
