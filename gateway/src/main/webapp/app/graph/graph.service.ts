import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class GraphService {
    private resourceUrl = 'noseservice/api/lastSecQps';

    constructor(private http: Http) { }

    get(): Observable<Response> {
        return this.http.get(this.resourceUrl);
    }


}
