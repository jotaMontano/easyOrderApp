import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IListOfValue } from 'app/shared/model/list-of-value.model';

type EntityResponseType = HttpResponse<IListOfValue>;
type EntityArrayResponseType = HttpResponse<IListOfValue[]>;

@Injectable({ providedIn: 'root' })
export class ListOfValueService {
    public resourceUrl = SERVER_API_URL + 'api/list-of-values';

    constructor(protected http: HttpClient) {}

    create(listOfValue: IListOfValue): Observable<EntityResponseType> {
        return this.http.post<IListOfValue>(this.resourceUrl, listOfValue, { observe: 'response' });
    }

    update(listOfValue: IListOfValue): Observable<EntityResponseType> {
        return this.http.put<IListOfValue>(this.resourceUrl, listOfValue, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IListOfValue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IListOfValue[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    public getLstProducts(): Observable<HttpResponse<IListOfValue[]>> {
        return this.http.get<IListOfValue[]>(`${this.resourceUrl}/LST_PRODUCTS`, { observe: 'response' });
    }
}
