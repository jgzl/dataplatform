export interface UserState {
  userId: string;
  token: string;
  roles: string[] | null;
  permissions: string[] | null;
  userName: string;
  nickName: string;
  avatar: string;
}