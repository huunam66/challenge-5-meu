export class ProductModel {
  public id: number = 0;
  public code: string = '';
  public name: string = '';
  public category: string = '';
  public brand: string = '';
  public type: string = '';
  public description: string = '';
  public created_at: Date = new Date();
  public updated_at: Date = new Date();
}
