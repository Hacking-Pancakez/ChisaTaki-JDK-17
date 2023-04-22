package dev.kurumiDisciples.chisataki.enums;

import java.util.List;

public enum RoleEnum {
  BOT_DEV("1044358875039666316"),
  CSTK_GUARD("1038887805234978897"),
  CSTK_STAFF("1016048811581382676"),
  PRESIDENT("1016047777098256435"),
  VICE_PRESIDENT("1016048573621739520"),
  GUNDAM("1033859722723467422"),
  VINLAND("1042588408083660852"),
  BOFURI("1042589219333361758"),
  KUBO("1056970896251818035"),
  YURI_IS_MY_JOB("1091172952046850148"),
  BIRDIE_WING("1091173216443191296"),
  YAMADA_KUN("1091173294880862268"),
  MAGICAL_DESTROYERS("1091173409783808020"),
  OSHI_NO_KO("1099397868122554409");

  private String id;

  private RoleEnum(String id) {
		this.id = id;
	}

  public String getId() {
    return this.id;
  }
  
  public static List<String> getStaffRoles() {
    return List.of(BOT_DEV.id, CSTK_GUARD.id, CSTK_STAFF.id, PRESIDENT.id, VICE_PRESIDENT.id);
  }
  
  public static List<String> getGroupWatchRoles() {
	    return List.of(GUNDAM.id, VINLAND.id, BOFURI.id, KUBO.id, YURI_IS_MY_JOB.id, BIRDIE_WING.id, YAMADA_KUN.id, MAGICAL_DESTROYERS.id, OSHI_NO_KO.id);
  }
}