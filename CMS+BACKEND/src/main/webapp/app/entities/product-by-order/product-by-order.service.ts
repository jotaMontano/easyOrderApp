import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';

type EntityResponseType = HttpResponse<IProductByOrder>;
type EntityArrayResponseType = HttpResponse<IProductByOrder[]>;

@Injectable({ providedIn: 'root' })
export class ProductByOrderService {
    public resourceUrl = SERVER_API_URL + 'api/product-by-orders';

    constructor(protected http: HttpClient) {}

    create(productByOrder: IProductByOrder): Observable<EntityResponseType> {
        return this.http.post<IProductByOrder>(this.resourceUrl, productByOrder, { observe: 'response' });
    }

    update(productByOrder: IProductByOrder): Observable<EntityResponseType> {
        return this.http.put<IProductByOrder>(this.resourceUrl, productByOrder, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductByOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductByOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
