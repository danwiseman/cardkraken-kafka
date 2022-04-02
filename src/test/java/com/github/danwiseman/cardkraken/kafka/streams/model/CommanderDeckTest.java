package com.github.danwiseman.cardkraken.kafka.streams.model;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import junit.framework.TestCase;
import org.junit.Test;

public class CommanderDeckTest extends TestCase {

    private String testDeck = "{" +
            "\"deck_id\":\"1896dcf1-0261-4f63-b214-77f5f1325cc4\"," +
            "\"game_type\":\"edh\"," +
            "\"source\":\"tapped-out\"," +
            "\"url\":\"https://tappedout.net/mtg-decks/30-06-21-pod/\"," +
            "\"cards\":[" +
            "{\"id\":\"8d59d264-87ee-4305-bffb-110549331a82\",\"qty\":\"1\",\"name\":\"Altar of the Brood\"}," +
            "{\"id\":\"0a508907-a127-45cd-898a-e936bc08391f\",\"qty\":\"1\",\"name\":\"Ancient Den\"}," +
            "{\"id\":\"bd3d4b4b-cf31-4f89-8140-9650edb03c7b\",\"qty\":\"1\",\"name\":\"Ancient Tomb\"}," +
            "{\"id\":\"fd061dd3-3cf1-4102-a9a2-dd89500171a7\",\"qty\":\"1\",\"name\":\"Arcane Signet\"}," +
            "{\"id\":\"cc258713-6ce3-44e0-9b4b-8fa7d1d093a1\",\"qty\":\"1\",\"name\":\"Archaeomancer&#x27;s Map\"}," +
            "{\"id\":\" \",\"qty\":\"1\",\"name\":\"Arcum&#x27;s Astrolabe\"}," +
            "{\"id\":\"046f2416-c206-4344-aa39-ec42585e0d64\",\"qty\":\"1\",\"name\":\"Ashnod&#x27;s Altar\"}," +
            "{\"id\":\"f79de5e7-1545-420c-bfe1-ee2444fca85b\",\"qty\":\"1\",\"name\":\"Basalt Monolith\"}," +
            "{\"id\":\"71e2f832-6601-4232-b250-fd1c88538fbd\",\"qty\":\"1\",\"name\":\"Blasting Station\"}," +
            "{\"id\":\"1f3dd48b-20a9-436a-a518-5eb77e17609c\",\"qty\":\"1\",\"name\":\"Buried Ruin\"}," +
            "{\"id\":\"c2e8d492-2c67-410b-b556-c157a14c4cec\",\"qty\":\"1\",\"name\":\"Chromatic Star\"}," +
            "{\"id\":\"f340cbf7-5bbe-45b9-a4bf-d1caa500ff93\",\"qty\":\"1\",\"name\":\"Chrome Mox\"}," +
            "{\"id\":\"6f880de5-8fc5-4e4e-a4bb-cc3e61a7697b\",\"qty\":\"1\",\"name\":\"Cloud Key\"}," +
            "{\"id\":\"2ba7597d-6d76-45b8-b172-342999e401bd\",\"qty\":\"1\",\"name\":\"Darksteel Citadel\"}," +
            "{\"id\":\"ebecd3d1-15ed-4501-89af-4718a212332d\",\"qty\":\"1\",\"name\":\"Dispeller&#x27;s Capsule\"}," +
            "{\"id\":\"98b0a4a8-9319-451b-9b79-b0bca7a41e91\",\"qty\":\"1\",\"name\":\"Drannith Magistrate\"}," +
            "{\"id\":\"27c5381c-2e79-41fa-b1d7-2cf0c6dc1808\",\"qty\":\"1\",\"name\":\"Dross Scorpion\"}," +
            "{\"id\":\"6741ab27-9e1f-4aa5-96b9-b450eda7c5c2\",\"qty\":\"1\",\"name\":\"Elixir of Immortality\"}," +
            "{\"id\":\"57a966e3-c9ba-4105-a36b-54ca70ba9b77\",\"qty\":\"1\",\"name\":\"Enduring Renewal\"}," +
            "{\"id\":\"0c9ebec9-3474-4062-9607-2e2a72f78299\",\"qty\":\"1\",\"name\":\"Enlightened Tutor\"}," +
            "{\"id\":\"f3537373-ef54-4578-9d05-6216420ee349\",\"qty\":\"1\",\"name\":\"Esper Sentinel\"}," +
            "{\"id\":\"abc8e0f8-fdb9-4f24-a3e3-439f6cc3ebdc\",\"qty\":\"1\",\"name\":\"Ethersworn Canonist\"}," +
            "{\"id\":\"9cd8112f-21e0-4272-9f0d-14d1ef989779\",\"qty\":\"1\",\"name\":\"Everflowing Chalice\"}," +
            "{\"id\":\"a0acea27-88de-4d27-8da2-8f82439526a1\",\"qty\":\"1\",\"name\":\"Flagstones of Trokair\"}," +
            "{\"id\":\"412d8300-926a-4f94-9edf-e45f07349fb9\",\"qty\":\"1\",\"name\":\"Foundry Inspector\"}," +
            "{\"id\":\"6a4f5a28-0bd2-4cc4-b67f-324e89193caa\",\"qty\":\"1\",\"name\":\"General Jarkeld\"}," +
            "{\"id\":\"93bd7e73-63dc-45b2-aaa8-984be8b900ab\",\"qty\":\"1\",\"name\":\"Genesis Chamber\"}," +
            "{\"id\":\"df1df511-b52c-45cd-9503-ffce4271a802\",\"qty\":\"1\",\"name\":\"Grinding Station\"}," +
            "{\"id\":\"41bba882-39b8-42db-9a01-54c6712b8019\",\"qty\":\"1\",\"name\":\"Helm of Awakening\"}," +
            "{\"id\":\"1ccdb407-ac8f-4736-89d3-ab0d086096ea\",\"qty\":\"1\",\"name\":\"Ichor Wellspring\"}," +
            "{\"id\":\"2cb8eb0c-d7fa-4229-ae65-1890c77b2c7c\",\"qty\":\"1\",\"name\":\"Implement of Improvement\"}," +
            "{\"id\":\"f917de1f-76f1-4859-a9f3-bcda6a9ca130\",\"qty\":\"1\",\"name\":\"Inventors&#x27; Fair\"}," +
            "{\"id\":\"119a814f-0325-4b69-a0b6-4c623b981f4a\",\"qty\":\"1\",\"name\":\"Junk Diver\"}," +
            "{\"id\":\"c60174d6-1f9d-4870-b3db-34d6fcb3f6ab\",\"qty\":\"1\",\"name\":\"Krark-Clan Ironworks\"}," +
            "{\"id\":\"95bba319-5f66-4085-9f2a-ab71f727ab64\",\"qty\":\"1\",\"name\":\"Land Tax\"}," +
            "{\"id\":\"e5ed8037-0450-4668-9f50-3abf367d7661\",\"qty\":\"1\",\"name\":\"Lightning Greaves\"}," +
            "{\"id\":\"13c6101a-da40-4785-8ccb-4e779bbbdb55\",\"qty\":\"1\",\"name\":\"Liquimetal Torque\"}," +
            "{\"id\":\"f85ab5f9-508e-45de-8fa1-ce1f16552ffc\",\"qty\":\"1\",\"name\":\"Lotus Petal\"}," +
            "{\"id\":\"d27e8442-91ce-4106-bfc6-a1f6e0e34c2d\",\"qty\":\"1\",\"name\":\"Magewright&#x27;s Stone\"}," +
            "{\"id\":\"4d960186-4559-4af0-bd22-63baa15f8939\",\"qty\":\"1\",\"name\":\"Mana Crypt\"}," +
            "{\"id\":\"cfdf9706-acdb-48fc-89db-93bc50b3b9ad\",\"qty\":\"1\",\"name\":\"Mana Vault\"}," +
            "{\"id\":\"975459ba-e1c2-4800-a3fa-5c0cf8ce728f\",\"qty\":\"1\",\"name\":\"Memnite\"}," +
            "{\"id\":\"688f726b-f8c2-4a87-8aa9-0ad2ba912be3\",\"qty\":\"1\",\"name\":\"Mentor of the Meek\"}," +
            "{\"id\":\"724acbe2-4c82-4360-a738-38cc6aa0c4a4\",\"qty\":\"1\",\"name\":\"Mistveil Plains\"}," +
            "{\"id\":\"66024e69-ad60-4c9a-a0ca-da138d33ad80\",\"qty\":\"1\",\"name\":\"Mox Amber\"}," +
            "{\"id\":\"56001a36-126b-4c08-af98-a6cc4d84210e\",\"qty\":\"1\",\"name\":\"Mox Opal\"}," +
            "{\"id\":\"c11c16ff-1786-4426-86b0-d7beb7a71798\",\"qty\":\"1\",\"name\":\"Mycosynth Wellspring\"}," +
            "{\"id\":\"7f0149d4-0731-474a-a1c3-28c25e486c14\",\"qty\":\"1\",\"name\":\"Myr Retriever\"}," +
            "{\"id\":\"a3f2e2e5-5ed2-4040-92ba-ad1ac13f03f6\",\"qty\":\"1\",\"name\":\"Myr Sire\"}," +
            "{\"id\":\"924a24e7-91b8-4ceb-a136-7a765d98c994\",\"qty\":\"1\",\"name\":\"Mystic Forge\"}," +
            "{\"id\":\"91e7faa4-160e-47d9-a9a1-5928d9d2b5e4\",\"qty\":\"1\",\"name\":\"Origin Spellbomb\"}," +
            "{\"id\":\"19cd6bcc-ca47-47cc-9fe4-c29e9c176485\",\"qty\":\"1\",\"name\":\"Paradise Mantle\"}," +
            "{\"id\":\"5a15c8ef-04ad-4aab-a7f1-c7a90c10eb50\",\"qty\":\"1\",\"name\":\"Perilous Myr\"}," +
            "{\"id\":\" \",\"qty\":\"1\",\"name\":\"Pilgrim&#x27;s Eye\"}," +
            "{\"id\":\"838ffc87-517a-4d94-8ce0-bc9ed01ecc52\",\"qty\":\"1\",\"name\":\"Rings of Brighthearth\"}," +
            "{\"id\":\"8dd2ebbe-71c6-405b-bad0-5680f0ff575c\",\"qty\":\"1\",\"name\":\"Salvager of Ruin\"}," +
            "{\"id\":\"f0336e07-8eaf-4b90-af54-4764e5ba50f2\",\"qty\":\"1\",\"name\":\"Salvaging Station\"}," +
            "{\"id\":\"bf1e1b54-2f97-4d33-807a-99ca38f21777\",\"qty\":\"1\",\"name\":\"Scrap Trawler\"}," +
            "{\"id\":\"dfaaa58d-89bb-4cb3-96a6-b480e6f6954e\",\"qty\":\"1\",\"name\":\"Scroll Rack\"}," +
            "{\"id\":\"0380b46d-1660-404d-9d11-705d8809ea46\",\"qty\":\"1\",\"name\":\"Semblance Anvil\"}," +
            "{\"id\":\" \",\"qty\":\"1\",\"name\":\"Sensei&#x27;s Divining Top\"}," +
            "{\"id\":\"af2299e0-d31f-4ec4-9497-16e494ee21e6\",\"qty\":\"1\",\"name\":\"Servo Schematic\"}," +
            "{\"id\":\"8854e9a7-a799-47d0-8662-95aa527d3210\",\"qty\":\"1\",\"name\":\"Skullclamp\"}," +
            "{\"id\":\"cab69fbd-0179-4b02-adba-71d2a0eeea5c\",\"qty\":\"1\",\"name\":\"Skyscanner\"}," +
            "{\"id\":\"38d347b7-dc17-417a-ab07-29fe99b9a101\",\"qty\":\"1\",\"name\":\"Sol Ring\"}," +
            "{\"id\":\"e9a8ca49-fc8f-49c1-a7c6-c8326b2deb52\",\"qty\":\"1\",\"name\":\"Spawning Pit\"}," +
            "{\"id\":\"fde838c8-2f32-4e7d-a236-0bc42dd7abd9\",\"qty\":\"1\",\"name\":\"Staff of Domination\"}," +
            "{\"id\":\"c1f5dd1b-bc42-44a7-aabf-b1b9a5db584d\",\"qty\":\"1\",\"name\":\"Teshar, Ancestor&#x27;s Apostle\"}," +
            "{\"id\":\"c1ab3225-64a9-411e-b22b-1869e958b8e5\",\"qty\":\"1\",\"name\":\"Thornbite Staff\"}," +
            "{\"id\":\"95c2d832-5244-4236-81d5-2920aa2e281e\",\"qty\":\"1\",\"name\":\"Thousand-Year Elixir\"}," +
            "{\"id\":\"2be39749-ad6f-4160-99eb-c677eee7f1b2\",\"qty\":\"1\",\"name\":\"Thraben Inspector\"}," +
            "{\"id\":\"10e35711-aec9-4024-a2a6-9efff8c71df2\",\"qty\":\"1\",\"name\":\"Umbral Mantle\"}," +
            "{\"id\":\"abcef9d1-7f5d-4fe8-b17d-f97c1b30f4a7\",\"qty\":\"1\",\"name\":\"Urza&#x27;s Bauble\"}," +
            "{\"id\":\"abcef9d1-7f5d-4fe8-b17d-f97c1b30f4a7\",\"qty\":\"1\",\"name\":\"Urza&#x27;s Saga\"}," +
            "{\"id\":\"48d6ce7c-5dc8-449b-acbd-db259ae687ed\",\"qty\":\"1\",\"name\":\"War Room\"}," +
            "{\"id\":\"e9387e8f-0e72-4212-81c8-e64050700c52\",\"qty\":\"1\",\"name\":\"Workshop Assistant\"}" +
            "],\"commanders\":[\"General Jarkeld\"]}";
    @Test
    public void testGetCardIds() {
        Gson gson = new Gson();
        CommanderDeck deck = gson.fromJson(testDeck, CommanderDeck.class);
        System.out.println(deck.getCardIds().toString());
        assertEquals(deck.getCardIds().size(), 76);

    }
}