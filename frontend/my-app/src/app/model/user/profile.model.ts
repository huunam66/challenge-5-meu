import { ProfileLocation } from "./profile-location.model";

export class Profile{
  id: string = '';
  first_name: string = '';
  last_name: string = '';
  identification_code: string = '';
  birthDay: Date = new Date();
  gender: string = '';
  avatar: string = '';
  email: string = '';
  profileLocation: ProfileLocation = new ProfileLocation();
}
