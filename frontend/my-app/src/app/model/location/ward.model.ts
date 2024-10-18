import { District } from "./district.model";

export class Ward {
  id: string = '';
  name: string = '';
  name_en: string = '';
  full_name: string = '';
  full_name_en: string = '';
  code_name: string = '';

  district: District = new District();
}
