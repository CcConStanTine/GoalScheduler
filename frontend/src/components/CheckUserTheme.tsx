import { ApplicationThemeOptions, ApplicationThemesColours } from "../utils/enums";

const checkUsedTheme = (theme: string): void => {
    const rootClassList = document.getElementById('root')?.classList;
    const bodyStyle = document.body.style;

    if (theme === ApplicationThemeOptions.DARK) {
        rootClassList!.add(ApplicationThemeOptions.DARK);
        bodyStyle.backgroundColor = ApplicationThemesColours.DARK;
    }
    else {
        rootClassList!.remove(ApplicationThemeOptions.DARK)
        bodyStyle.backgroundColor = ApplicationThemesColours.LIGHT;
    }
};

export default checkUsedTheme;