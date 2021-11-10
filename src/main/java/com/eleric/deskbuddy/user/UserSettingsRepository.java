package com.eleric.deskbuddy.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserSettingsRepository extends	CrudRepository<UserSettings, Integer> {
}
