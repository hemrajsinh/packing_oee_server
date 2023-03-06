package in.sparklogic.constants;

public interface Constants
{
	public String USER_ROLE = "USER";
	public String ADMIN_ROLE = "ADMIN";
	public int HTTPS_PORT = 8443;
	public String COMP_ID = "VMS";
	public String SECRET_KEY = COMP_ID+"APPLICATION";
	public String PREFIX_KEY = "Bearer";
	public String ENVIRONMENT_PRODUCTION = "production";

	public String CREATOR_NAME = COMP_ID;
	public String KEY_FIELD_NAME = "MSG";
	public String MESSAGE_CONTENT = COMP_ID+" Notification";

	public String Y = "Y";
	public String N = "N";
	
	public String ADD = "Add";
	public String EDIT = "Edit";
	public String DELETE = "Delete";
	
	public String INVOICE = "Invoice";
	public String ACCOUNT = "Account";
	public String PETTYCASH = "PettyCash";
	public String LOAN = "Loan";
	public String MARKETING = "Marketing";
	public String TENDER = "Tender";
	public String SALARY = "Salary";
	public String PURCHASE = "Purchase";
	public String OFFICER = "Officer";
	public String TRANSPORT = "Transport";
	public String SALES = "Sales";
	public String LOANREPAYMENT = "LoanRepayment";
	
	public String PAYMENT = "Payment";
	public String EXPENSE = "Expense";
	public String INCOME = "Income";
	public String RECEIVED = "Received";
	
	public String MACHINE_IN = "In";
	public String MACHINE_OUT = "Out";
	public String MACHINE_ACTIVE = "Active";
	
	public String VENDOR = "Vendor";
	public String TRANSPORTER = "Transporter";
	public String SUPPLIER = "Supplier";
	public String CUSTOMER = "Customer";
	public String SERVICE_PROVIDER = "Service Provider";
	public String SUB_CONTRACTOR = "Sub Contractor";
	
	public String GRADE_MANAGEMENT = "Management";
	public String GRADE_STAFF = "Staff";
	public String GRADE_OPERATOR = "Operator";
	public String GRADE_HELPER = "Helper";
	
	public String STATUS_APPROVED = "Approved";
	public String STATUS_REJECTED = "Rejected";
	public String STATUS_PENDING = "Pending";
	
	public String TRANSPORTATION_FREIGHT = "Freight";
	public String TRANSPORTATION_PAYMENT = "Payment";
	
	public Long SUPER_ADMIN = 1000l;
	
	public String PETTYCASH_CREDIT = "Credit";
	public String PETTYCASH_PAID = "Paid";
	
	public int USER_APPROVED = 10;
	public int DEPT_HEAD_APPROVED = 20;
	public int UNIT_HEAD_APPROVED = 30;
	public int SECURITY_HEAD_APPROVED = 40;
	
	
	public enum USER_TYPE {
		SUPER_ADMIN("SUPER ADMIN"), BO_ADMIN("BO ADMIN"), BO_TEACHER("BO TEACHER"), ADMIN("ADMIN"), SUB_ADMIN("SUB ADMIN"), TEACHER("TEACHER"), STUDENT("STUDENT");

		String str;

		USER_TYPE(String str) {
			this.str = str;
		}

		public String getValue() {
			return str;
		}
	}
	
	
	public enum HARDNESS_LEVEL {
		EASY("EASY"), MEDIUM("MEDIUM"), HARD("HARD");

		String str;

		HARDNESS_LEVEL(String str) {
			this.str = str;
		}

		public String getValue() {
			return str;
		}
	}
	
}

