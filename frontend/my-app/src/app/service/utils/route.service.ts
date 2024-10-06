import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class RouteService {

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }


  navigate(path: string, params: any): void {
    this.router.navigate([path], {
      queryParams: {
        ...params,
      },
      queryParamsHandling: 'replace',
      replaceUrl: true,
    });
  }

  query(func: Function): void {
    this.route.queryParams.pipe().subscribe((query) => func(query));
  }

}
