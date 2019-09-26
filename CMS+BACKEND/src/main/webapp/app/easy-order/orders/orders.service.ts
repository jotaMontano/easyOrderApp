import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IOrderOk, OrderOk } from 'app/shared/model/order-ok.model';

type EntityResponseType = HttpResponse<IOrderOk>;
type EntityArrayResponseType = HttpResponse<IOrderOk[]>;

@Injectable()
export class OrdersService {
    private resourceUrl = SERVER_API_URL + 'api/ordersList';

    constructor(private http: HttpClient) {}

    getOrders(id: number): Observable<EntityArrayResponseType> {
        return this.http.get<IOrderOk[]>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    private convertArrayResponse(res: HttpResponse<OrderOk[]>): HttpResponse<OrderOk[]> {
        const jsonResponse: OrderOk[] = res.body;
        const body: OrderOk[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }
    private convertItemFromServer(orderOk: OrderOk): OrderOk {
        const copy: OrderOk = Object.assign({}, orderOk);
        return copy;
    }
    // query(req?: any): Observable<EntityArrayResponseType> {
    //     const options = createRequestOption(req);
    //     return this.http.get<IOrderOk[]>(this.resourceUrl, { params: options, observe: 'response' });
    // }
}
