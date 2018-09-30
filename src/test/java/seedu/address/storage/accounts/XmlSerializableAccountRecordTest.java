package seedu.address.storage.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.testutil.accounts.TypicalAccounts;

public class XmlSerializableAccountRecordTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableAccountRecordTest");
    private static final Path TYPICAL_ACCOUNTS_FILE = TEST_DATA_FOLDER.resolve("typicalAccountRecord.xml");
    private static final Path INVALID_ACCOUNTS_FILE = TEST_DATA_FOLDER.resolve("invalidAccountRecord.xml");
    private static final Path DUPLICATE_ACCOUNTS_FILE = TEST_DATA_FOLDER.resolve("duplicateAccountRecord.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private XmlSerializableAccountRecord dataFromFile = null;

    @Test
    public void toModelType_typicalAccountRecordFile_success() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ACCOUNTS_FILE, XmlSerializableAccountRecord.class);
        AccountRecord accountRecordFromFile = dataFromFile.toModelType();
        AccountRecord typicalAccountRecord = TypicalAccounts.getTypicalAccountRecord();
        System.out.println(accountRecordFromFile.getAccountList() + " " + typicalAccountRecord.getAccountList());
        assertEquals(accountRecordFromFile, typicalAccountRecord);
    }

    @Test
    public void toModelType_invalidAccountRecordFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_ACCOUNTS_FILE, XmlSerializableAccountRecord.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ACCOUNTS_FILE, XmlSerializableAccountRecord.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAccountRecord.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ACCOUNTS_FILE, XmlSerializableAccountRecord.class);
        AccountRecord accountRecordFromFile = dataFromFile.toModelType();
        AccountRecord typicalAccountRecord = TypicalAccounts.getTypicalAccountRecord();

        // same object
        assertEquals(accountRecordFromFile, accountRecordFromFile);

        // different object, same state
        assertEquals(accountRecordFromFile, typicalAccountRecord);

        typicalAccountRecord.removeAccount(DEMO_ADMIN);
        assertNotEquals(accountRecordFromFile, typicalAccountRecord);

        // not same instance
        assertNotEquals(1, accountRecordFromFile);

        // not same instance
        assertNotEquals(null, accountRecordFromFile);
    }
}
