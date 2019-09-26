import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExtra } from 'app/shared/model/extra.model';
import { ICategory } from 'app/shared/model/category.model';

type EntityResponseType = HttpResponse<IExtra>;
type EntityArrayResponseType = HttpResponse<IExtra[]>;

@Injectable({ providedIn: 'root' })
export class ExtrasService {
    public resourceUrl = SERVER_API_URL + 'api/extras';

    constructor(protected http: HttpClient) {}

    create(extra: IExtra): Observable<EntityResponseType> {
        return this.http.post<IExtra>(this.resourceUrl, extra, { observe: 'response' });
    }

    update(extra: IExtra): Observable<EntityResponseType> {
        return this.http.put<IExtra>(this.resourceUrl, extra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExtra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExtra[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    public findExtrasByClient(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExtra[]>(`${this.resourceUrl}ByClient/${id}`, { params: options, observe: 'response' });
    }

    public getExtrasByStatus(id: number, status: boolean, req?: any): Observable<HttpResponse<IExtra[]>> {
        const options = createRequestOption(req);
        return this.http.get<IExtra[]>(`${this.resourceUrl}/getExtrasByStatus/${id}/${status}`, {
            params: options,
            observe: 'response'
        });
    }
}
