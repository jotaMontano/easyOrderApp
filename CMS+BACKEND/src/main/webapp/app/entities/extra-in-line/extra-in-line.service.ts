import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExtraInLine } from 'app/shared/model/extra-in-line.model';

type EntityResponseType = HttpResponse<IExtraInLine>;
type EntityArrayResponseType = HttpResponse<IExtraInLine[]>;

@Injectable({ providedIn: 'root' })
export class ExtraInLineService {
    public resourceUrl = SERVER_API_URL + 'api/extra-in-lines';

    constructor(protected http: HttpClient) {}

    create(extraInLine: IExtraInLine): Observable<EntityResponseType> {
        return this.http.post<IExtraInLine>(this.resourceUrl, extraInLine, { observe: 'response' });
    }

    update(extraInLine: IExtraInLine): Observable<EntityResponseType> {
        return this.http.put<IExtraInLine>(this.resourceUrl, extraInLine, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExtraInLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExtraInLine[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
