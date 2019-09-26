import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderOk, OrderOk } from 'app/shared/model/order-ok.model';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { IProduct } from 'app/shared/model/product.model';
import { IExtraInLine } from 'app/shared/model/extra-in-line.model';

type EntityResponseType = HttpResponse<IOrderOk>;
type EntityArrayResponseType = HttpResponse<IOrderOk[]>;

@Injectable({ providedIn: 'root' })
export class OrderOkService {
    public resourceUrl = SERVER_API_URL + 'api/order-oks';

    constructor(protected http: HttpClient) {}

    create(orderOk: IOrderOk): Observable<EntityResponseType> {
        return this.http.post<IOrderOk>(this.resourceUrl, orderOk, { observe: 'response' });
    }

    update(orderOk: IOrderOk): Observable<EntityResponseType> {
        return this.http.put<IOrderOk>(this.resourceUrl, orderOk, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrderOk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrderOk[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    getOrders(id: number, filter: string): Observable<EntityArrayResponseType> {
        return this.http.get<IOrderOk[]>(`${SERVER_API_URL}api/ordersList/${id}/${filter}`, { observe: 'response' });
    }
    getOrderLine(id: number): Observable<HttpResponse<IProductByOrder[]>> {
        return this.http.get<IProductByOrder[]>(`${SERVER_API_URL}api/productsByOrder/${id}`, { observe: 'response' });
    }
    getProductByOrder(id: number): Observable<HttpResponse<IProduct>> {
        return this.http.get<IProduct>(`${SERVER_API_URL}api/products/${id}`, { observe: 'response' });
    }
    getExtraLine(id: number): Observable<HttpResponse<IExtraInLine[]>> {
        return this.http.get<IExtraInLine[]>(`${SERVER_API_URL}api/extraLine/${id}`, { observe: 'response' });
    }
    updateOrder(order: IOrderOk): Observable<EntityResponseType> {
        return this.http.put<IOrderOk>(`${SERVER_API_URL}api/updateOrder`, order, { observe: 'response' });
    }
    getExtraByOrder(id: number): Observable<HttpResponse<IProduct>> {
        return this.http.get<IProduct>(`${SERVER_API_URL}api/extras/${id}`, { observe: 'response' });
    }
}
