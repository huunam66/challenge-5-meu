
export interface Province {
  id?: string;
  name?: string;
  name_en?: string;
  full_name?: string;
  full_name_en?: string;
  code_name?: string;

  districts: District[];
}

export interface District {
  id?: string;
  name?: string;
  name_en?: string;
  full_name?: string;
  full_name_en?: string;
  code_name?: string;

  province?: Province;

  wards: Ward[];
}

export interface Ward {
  id?: string;
  name?: string;
  name_en?: string;
  full_name?: string;
  full_name_en?: string;
  code_name?: string;

  district?: District;
}
