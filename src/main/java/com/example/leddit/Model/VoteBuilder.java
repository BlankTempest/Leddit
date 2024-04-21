package com.example.leddit.Model;

public class VoteBuilder {
    private Vote vote;

    public VoteBuilder() {
        vote = new Vote();
    }

    public VoteBuilder user(User user) {
        vote.setUser(user);
        return this;
    }

    public VoteBuilder post(Post post) {
        vote.setPost(post);
        return this;
    }

    public VoteBuilder voteType(VoteType voteType) {
        vote.setVoteType(voteType);
        return this;
    }

    public Vote build() {
        return vote;
    }
}