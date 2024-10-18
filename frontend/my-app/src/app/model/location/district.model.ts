import { Province } from "./province.model";
import { Ward } from "./ward.model";

export class District {
  id: string = '';
  name: string = '';
  name_en: string = '';
  full_name: string = '';
  full_name_en: string = '';
  code_name: string = '';

  province: Province = new Province();

  wards: Ward[] = [];
}
