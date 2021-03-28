package edu.regent.shiritoribot.discord;

import edu.regent.shiritoribot.ShiritoriBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ShiritoriPlayer extends ListenerAdapter {
    private Member member;
    private String originalNickname;

    private PlayerStatus status;

    public ShiritoriPlayer(Member member) {
        this.member = member;
        originalNickname = member.getEffectiveName();
    }

    public void init() {
        ShiritoriBot.jda.addEventListener(this);
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
        try {
            member.modifyNickname(originalNickname + status.getSuffix()).complete();
        } catch (Exception e) {
            //do nothing (owner doesnt work correctly)
        }
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return originalNickname;
    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if(!event.getMember().equals(member)) return;
    }

    public void destroy() {
        ShiritoriBot.jda.removeEventListener(this);
        member.modifyNickname(originalNickname).queue();
    }
}
