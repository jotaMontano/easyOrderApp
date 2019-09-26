import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITop } from 'app/shared/model/top.model';
import { IProduct } from 'app/shared/model/product.model';
import { IClient } from 'app/shared/model/client.model';

type EntityResponseType = HttpResponse<ITop>;
type EntityArrayResponseType = HttpResponse<ITop[]>;

@Injectable({ providedIn: 'root' })
export class TopService {
    public resourceUrl = SERVER_API_URL + 'api/tops';

    constructor(protected http: HttpClient) {}

    create(top: ITop): Observable<EntityResponseType> {
        return this.http.post<ITop>(this.resourceUrl, top, { observe: 'response' });
    }

    update(top: ITop): Observable<EntityResponseType> {
        return this.http.put<ITop>(this.resourceUrl, top, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITop>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITop[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    public getTopByIdProduct(id: number, req?: any): Observable<HttpResponse<ITop>> {
        const options = createRequestOption(req);
        return this.http.get<ITop>(`${this.resourceUrl}/getTopByIdProduct/${id}`, { params: options, observe: 'response' });
    }
}
