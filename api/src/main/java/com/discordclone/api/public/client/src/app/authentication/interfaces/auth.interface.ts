export interface RegisterFields {
  email: string;
  name: string;
  password: string;
}

export interface LoginFields {
  username: string;
  password: string;
}

export interface UserDto {
  createdAt: string;
  email: string;
  id: string;
  imageUrl: string;
  name: string;
  updatedAt: string;
}

export interface UserDef {
  createdAt: string;
  email: string;
  id: string;
  imageUrl: string;
  name: string;
  updatedAt: string;
}

export interface UpdateProfileDto {
  profileImage?: string;
  username: string;
}
