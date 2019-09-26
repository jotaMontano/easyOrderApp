import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderHistory } from 'app/shared/model/order-history.model';

type EntityResponseType = HttpResponse<IOrderHistory>;
type EntityArrayResponseType = HttpResponse<IOrderHistory[]>;

@Injectable({ providedIn: 'root' })
export class OrderHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/order-histories';

    constructor(protected http: HttpClient) {}

    create(orderHistory: IOrderHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderHistory);
        return this.http
            .post<IOrderHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(orderHistory: IOrderHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderHistory);
        return this.http
            .put<IOrderHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrderHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrderHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(orderHistory: IOrderHistory): IOrderHistory {
        const copy: IOrderHistory = Object.assign({}, orderHistory, {
            payDate: orderHistory.payDate != null && orderHistory.payDate.isValid() ? orderHistory.payDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.payDate = res.body.payDate != null ? moment(res.body.payDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((orderHistory: IOrderHistory) => {
                orderHistory.payDate = orderHistory.payDate != null ? moment(orderHistory.payDate) : null;
            });
        }
        return res;
    }
}
