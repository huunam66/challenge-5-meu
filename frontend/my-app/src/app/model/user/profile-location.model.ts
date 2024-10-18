import { Ward } from "../location/ward.model";

export class ProfileLocation {
  id: string = '';
  home_number: string = '';
  street: string = '';
  country: string = '';
  ward: Ward = new Ward();
}
