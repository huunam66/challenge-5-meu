import { Ward } from "./location.model";


export interface Profile {
  id: string;
  first_name: string;
  last_name: string;
  identification_code: string;
  birthDay: Date;
  gender: 'Nam' | 'Nữ';
  avatar: string;
  email: string;
  profileLocation: ProfileLocation;
}


export interface ProfileLocation {
  id: string;
  home_number: string;
  street: string;
  country: string;
  ward: Ward;
}

export interface AvatarResult {
  avatar: string;
  uploaded: true | false;
}
