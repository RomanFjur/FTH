/*  By Pavel Kisliuk, 11.07.2019
 *  This is class for education and nothing rights don't reserved.
 */

package com.pavelkisliuk.fth.specifier.insert;

import com.lambdaworks.crypto.SCryptUtil;
import com.pavelkisliuk.fth.exception.FthRepositoryException;
import com.pavelkisliuk.fth.model.FthRegistrationData;
import com.pavelkisliuk.fth.repository.FthRepository;
import com.pavelkisliuk.fth.specifier.FthInsertSpecifier;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The class {@code RegistrationDataInsertSpecifier} is realization of {@code FthInsertSpecifier}
 * for insertion data into RegistrationData table of database by {@code FthRepository}.
 * <p>
 *
 * @author Kisliuk Pavel Sergeevich
 * @see FthRepository
 * @see com.pavelkisliuk.fth.specifier.FthSpecifier
 * @see com.pavelkisliuk.fth.specifier.FthInsertSpecifier
 * @see com.pavelkisliuk.fth.model.FthRegistrationData
 * @since 12.0
 */
public class RegistrationDataInsertSpecifier implements FthInsertSpecifier {
	/**
	 * Insert request to database.
	 */
	private static final String REQUEST = "INSERT INTO RegistrationData " +
			"(firstName, lastName, eMail, password, registrationKey) " +
			"VALUES (?, ?, ?, ?, ?)";

	/**
	 * The {@code FthRegistrationData} instance for SQL insertion.
	 */
	private FthRegistrationData data;

	/**
	 * Constructor for {@code data} initializing.
	 * <p>
	 *
	 * @param data for {@code data} initializing.
	 */
	public RegistrationDataInsertSpecifier(FthRegistrationData data) {
		this.data = data;
	}

	/**
	 * Paste metadata in {@code PreparedStatement} to insert this data to database.
	 * <p>
	 *
	 * @param statement for pasting metadata into.
	 * @throws FthRepositoryException if {@code SQLException} occurred.
	 */
	@Override
	public void insert(PreparedStatement statement) throws FthRepositoryException {
		int N = 16;
		int r = 16;
		int p = 16;
		data.setPassword(SCryptUtil.scrypt(data.getPassword(), N, r, p));
		try {
			statement.setString(1, data.getName());
			statement.setString(2, data.getSurname());
			statement.setString(3, data.getEmail());
			statement.setString(4, data.getPassword());
			statement.setString(5, data.getKey());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new FthRepositoryException(
					"SQL exception in RegistrationDataInsertSpecifier -> insert().", e);
		}
	}

	/**
	 * Return SQL request as String.
	 * <p>
	 *
	 * @return SQL request for {@code PreparedStatement} as String.
	 */
	@Override
	public String deriveSequelRequest() {
		return REQUEST;
	}
}