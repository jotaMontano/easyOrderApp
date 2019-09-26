import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExtraInLine } from 'app/shared/model/extra-in-line.model';
import { ExtraInLineService } from './extra-in-line.service';
import { ExtraInLineComponent } from './extra-in-line.component';
import { ExtraInLineDetailComponent } from './extra-in-line-detail.component';
import { ExtraInLineUpdateComponent } from './extra-in-line-update.component';
import { ExtraInLineDeletePopupComponent } from './extra-in-line-delete-dialog.component';
import { IExtraInLine } from 'app/shared/model/extra-in-line.model';

@Injectable({ providedIn: 'root' })
export class ExtraInLineResolve implements Resolve<IExtraInLine> {
    constructor(private service: ExtraInLineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExtraInLine> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExtraInLine>) => response.ok),
                map((extraInLine: HttpResponse<ExtraInLine>) => extraInLine.body)
            );
        }
        return of(new ExtraInLine());
    }
}

export const extraInLineRoute: Routes = [
    {
        path: '',
        component: ExtraInLineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.extraInLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExtraInLineDetailComponent,
        resolve: {
            extraInLine: ExtraInLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extraInLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExtraInLineUpdateComponent,
        resolve: {
            extraInLine: ExtraInLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extraInLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExtraInLineUpdateComponent,
        resolve: {
            extraInLine: ExtraInLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extraInLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const extraInLinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExtraInLineDeletePopupComponent,
        resolve: {
            extraInLine: ExtraInLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.extraInLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
