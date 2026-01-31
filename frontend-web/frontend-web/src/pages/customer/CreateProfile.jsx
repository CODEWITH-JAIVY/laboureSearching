import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import axios from "../../utils/axios";
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

const validationSchema = Yup.object({
  address: Yup.object({
    city: Yup.string().required("City required"),
    local: Yup.string().required("Local area required"),
    landmark: Yup.string(),
    postalCode: Yup.string().required("Postal code required"),
  }),
});

export default function CreateCustomerProfile() {
  const navigate = useNavigate();
  const { refreshUser } = useContext(AuthContext);

  const initialValues = {
    address: {
      city: "",
      local: "",
      landmark: "",
      postalCode: "",
    },
    liveLocation: {
      latitude: "",
      longitude: "",
    },
  };

  const submit = async (values) => {
    try {
      await axios.post("/customer/profile", values);
      await refreshUser();
      navigate("/customer/dashboard");
    } catch (e) {
      alert("Customer profile create failed");
    }
  };

  return (
    <div className="max-w-xl mx-auto bg-white p-6 rounded shadow">
      <h2 className="text-2xl font-bold mb-4">Create Customer Profile</h2>

      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={submit}
      >
        <Form className="space-y-4">
          <Field name="address.city" placeholder="City" className="input" />
          <ErrorMessage name="address.city" component="p" className="error" />

          <Field
            name="address.local"
            placeholder="Local Area"
            className="input"
          />
          <Field
            name="address.landmark"
            placeholder="Landmark"
            className="input"
          />
          <Field
            name="address.postalCode"
            placeholder="Postal Code"
            className="input"
          />

          <h3 className="font-semibold mt-3">Live Location</h3>
          <Field
            name="liveLocation.latitude"
            placeholder="Latitude"
            className="input"
          />
          <Field
            name="liveLocation.longitude"
            placeholder="Longitude"
            className="input"
          />

          <button
            type="submit"
            className="w-full bg-green-600 text-white py-2 rounded"
          >
            Save & Continue
          </button>
        </Form>
      </Formik>
    </div>
  );
}
