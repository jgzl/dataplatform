import LayoutStore from "@/layouts";
import Cookies from "js-cookie";

import Avatar from "@/assets/img_avatar.gif";
import { defineStore } from "pinia";
import { UserState } from "../types";
import {
  ROLE_ID_KEY,
  USER_ID_KEY,
  USER_INFO_KEY,
  USER_TOKEN_KEY,
} from "../keys";

const defaultAvatar = Avatar;

const userInfo: UserState = JSON.parse(
  localStorage.getItem(USER_INFO_KEY) || "{}"
);

const useUserStore = defineStore("user", {
  state: () => {
    return {
      userId: userInfo.userId || "0",
      roles: userInfo.roles || null,
      permissions: userInfo.permissions || null,
      token: userInfo.token || "",
      userName: userInfo.userName || "",
      nickName: userInfo.nickName || "",
      avatar: userInfo.avatar || defaultAvatar,
    };
  },
  getters: {
    getUserId(): string {
      return this.userId;
    },
    getRoles(): string[] | null {
      return this.roles;
    },
    getPermissions(): string[] | null {
      return this.permissions
    },
  },
  actions: {
    saveUser(userInfo: UserState) {
      return new Promise<void>((res) => {
        this.userId = userInfo.userId;
        this.token = userInfo.token;
        this.roles = userInfo.roles;
        this.permissions = userInfo.permissions;
        this.userName = userInfo.userName;
        this.nickName = userInfo.nickName;
        this.avatar = userInfo.avatar || defaultAvatar;
        Cookies.set(USER_TOKEN_KEY, userInfo.token);
        localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo));
        res();
      });
    },
    changeNickName(newNickName: string) {
      this.nickName = newNickName;
    },
    logout() {
      return new Promise<void>((res) => {
        this.userId = "0";
        this.avatar = "";
        this.roles = [];
        this.userName = "";
        this.nickName = "";
        this.token = "";
        Cookies.remove(USER_TOKEN_KEY);
        localStorage.clear();
        LayoutStore.reset();
        res();
      });
    },
  },
});

export default useUserStore;
