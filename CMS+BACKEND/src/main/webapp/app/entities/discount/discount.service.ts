import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiscount } from 'app/shared/model/discount.model';
import { IClient } from 'app/shared/model/client.model';
import { IProduct } from 'app/shared/model/product.model';

type EntityResponseType = HttpResponse<IDiscount>;
type EntityArrayResponseType = HttpResponse<IDiscount[]>;

@Injectable({ providedIn: 'root' })
export class DiscountService {
    public resourceUrl = SERVER_API_URL + 'api/discounts';

    constructor(protected http: HttpClient) {}

    create(discount: IDiscount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(discount);
        return this.http
            .post<IDiscount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(discount: IDiscount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(discount);
        return this.http
            .put<IDiscount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDiscount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDiscount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(discount: IDiscount): IDiscount {
        const copy: IDiscount = Object.assign({}, discount, {
            starDate: discount.starDate != null && discount.starDate.isValid() ? discount.starDate.toJSON() : null,
            endDate: discount.endDate != null && discount.endDate.isValid() ? discount.endDate.toJSON() : null,
            startHour: discount.startHour != null && discount.startHour.isValid() ? discount.startHour.toJSON() : null,
            endHour: discount.endHour != null && discount.endHour.isValid() ? discount.endHour.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.starDate = res.body.starDate != null ? moment(res.body.starDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
            res.body.startHour = res.body.startHour != null ? moment(res.body.startHour) : null;
            res.body.endHour = res.body.endHour != null ? moment(res.body.endHour) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((discount: IDiscount) => {
                discount.starDate = discount.starDate != null ? moment(discount.starDate) : null;
                discount.endDate = discount.endDate != null ? moment(discount.endDate) : null;
                discount.startHour = discount.startHour != null ? moment(discount.startHour) : null;
                discount.endHour = discount.endHour != null ? moment(discount.endHour) : null;
            });
        }
        return res;
    }
    public findDiscountByUser(id: number, req?: any): Observable<HttpResponse<IDiscount[]>> {
        const options = createRequestOption(req);
        return this.http.get<IDiscount[]>(`${this.resourceUrl}/findDiscountByUser/${id}`, { params: options, observe: 'response' });
    }
}
