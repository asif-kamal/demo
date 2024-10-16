import React, { useEffect, useState } from "react";
import "./RegFormOne.css";
import { TextInput } from "../../../../components/TextInput/TextInput";
import { ValidatedInput } from "../../../../components/ValidatedInput/ValidatedInput";

interface FormOneState {
  firstName: string;
  lastName: string;
  email: string;
  dateOfBirth: string;
}

export const RegFormOne: React.FC = () => {
  const [stepOneState, setStepOneState] = useState<FormOneState>({
    firstName: "",
    lastName: "",
    email: "",
    dateOfBirth: "",
  });

  const updateUser = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setStepOneState({ ...stepOneState, [e.target.name]: e.target.value });
  };

  // useEffect(() => {
  //   console.log("State change: ", stepOneState); logs everytime any change occurs
  // }, [stepOneState]);

  return (
    <div className="reg-step-one-container">
      <div className="reg-step-one-content">
        <ValidatedInput
          name={"firstName"}
          label={"First Name"}
          errorMessage="Please enter your first name"
          changeValue={updateUser}
          validator={() => true}
        ></ValidatedInput>
        <ValidatedInput
          name={"lastName"}
          label={"Last Name"}
          errorMessage="Please enter your last name"
          changeValue={updateUser}
          validator={() => true}
        ></ValidatedInput>
        <ValidatedInput
          name={"email"}
          label={"Email"}
          errorMessage="Please enter a valid email address"
          changeValue={updateUser}
          validator={() => true}
        ></ValidatedInput>
      </div>
    </div>
  );
};
