import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Domain } from '../../models/domain';
import { Observable } from 'rxjs/Observable';
import { DataSource } from '@angular/cdk/collections';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-domain-list-recent',
  templateUrl: './domain-list-recent.component.html',
  styleUrls: ['./domain-list-recent.component.scss'],
})
export class DomainListRecentComponent implements OnInit {
  // #region interfaces

  // #endregion

  // #region constructor and lifecycle events

  constructor(
    private router: Router,
    private http: HttpClient,
    private auth: AuthService,
  ) {
    this.displayedColumns = ['name', 'description'];
    this.domainDataSource = new ViewDomainListDataSource(this.allDomains$);
  }

  ngOnInit() {
    this.reqGetAllDomains();
  }

  // #endregion

  // #region view binds

  public displayedColumns: string[];
  public domainDataSource: ViewDomainListDataSource;

  // #endregion

  // #region view events

  detail(id) {
    this.router.navigate(['/detail', id]);
  }

  // #endregion

  // #region privates
  private allDomains$: BehaviorSubject<Domain[]> = new BehaviorSubject<
    Domain[]
  >([]);

  private reqGetAllDomains() {
    this.http.get('/api/domains/recent').subscribe(
      (data: any) => {
        this.allDomains$.next(data);
      },
      error => {
        console.log(error);
        this.allDomains$.next([]);
      },
    );
  }

  // #endregion
}

class ViewDomainListDataSource extends DataSource<Domain> {
  // #region interfaces

  connect(): Observable<Domain[]> {
    return this.dataChange;
  }

  disconnect() {}

  // #endregion

  // #region constructor

  constructor(private dataChange: BehaviorSubject<Domain[]>) {
    super();
  }

  // #endregion

  // #region privates

  // #endregion
}
