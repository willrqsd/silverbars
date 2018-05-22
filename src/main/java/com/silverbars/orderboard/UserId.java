package com.silverbars.orderboard;

import java.util.Objects;

public class UserId {
    private final long userId;

    public UserId(long userId) {
        this.userId = userId;
    }

    public long asLong() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId1 = (UserId) o;
        return userId == userId1.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
