export interface ResponseResult<T> {
  status: boolean;
  code: number;
  message: string;
  data: T;
  timestamp: number;
}

export interface PageableFilter<T> {
  pageNumber: number;
  totalPages: number;
  offset: number;
  totalElements: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  content: T;
}
