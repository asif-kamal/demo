import styled from "styled-components";
import { StyledInputProps } from "../../../../utils/GlobalInterfaces";
import { determineStyleInputBorder } from "../../../../utils/DeterminedStylesUtils";

export const styledInputBox = styled.div<StyledInputProps>`
    position: relative,
    border-radius: 5px,
    width: 100%,
    height: 56px,
    border: ${(props) => determineStyleInputBorder(props)}
`